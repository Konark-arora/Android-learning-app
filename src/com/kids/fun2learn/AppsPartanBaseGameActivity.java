package com.kids.fun2learn;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.appspartan.inappbilling.util.IabHelper;
import com.appspartan.inappbilling.util.IabResult;
import com.appspartan.inappbilling.util.Inventory;
import com.appspartan.inappbilling.util.Purchase;
import com.kids.fun2learn.utils.CommonConstants;
import com.kids.fun2learn.utils.CommonUtils;
import com.kids.fun2learn.utils.EngineUtilits;
import com.kids.fun2learn.utils.NetworkUtils;
import com.kids.fun2learn.utils.SharedPrefUtils;

public class AppsPartanBaseGameActivity extends BaseGameActivity {

	protected MainMenuScene mainMenuScene;

	protected static final String TAG = "Learn2fun IN APP Purchase";

	// The helper object
	protected IabHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		// setUpInapp();

	}

	protected void showInAppPurchaseDialog() {

		// Creating alert Dialog with three Buttons

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				AppsPartanBaseGameActivity.this);

		// Setting Dialog Title
		alertDialog.setTitle("Save File...");

		// Setting Dialog Message
		alertDialog.setMessage("Do you want to save this file?");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.save);

		// Setting Positive Yes Button
		alertDialog.setPositiveButton("Buy",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// User pressed Cancel button. Write Logic Here
						// Toast.makeText(getApplicationContext(),
						// "You clicked on Buy", Toast.LENGTH_SHORT)
						// .show();

						if (NetworkUtils.isConnected(getApplicationContext())) {

							makeInAppPurchase();

						} else {

							CommonUtils.showToast(getApplicationContext(),
									CommonConstants.NO_NETWORK_MESSAGE);
						}
					}
				});
		// Setting Positive Yes Button
		alertDialog.setNeutralButton("Restore",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// User pressed No button. Write Logic Here
						// Toast.makeText(getApplicationContext(),
						// "You clicked on Restore", Toast.LENGTH_SHORT).show();

						if (NetworkUtils.isConnected(getApplicationContext())) {

							restoreInAppTransaction();

						} else {

							CommonUtils.showToast(getApplicationContext(),
									CommonConstants.NO_NETWORK_MESSAGE);
						}

					}
				});
		// Setting Positive "Cancel" Button
		alertDialog.setNegativeButton("Exit Game",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// User pressed Cancel button. Write Logic Here
						// Toast.makeText(getApplicationContext(),
						// "You clicked on Cancel", Toast.LENGTH_SHORT)
						// .show();
						exitAndFinishGame();
					}
				});
		// Showing Alert Message
		alertDialog.show();

	}

	protected void exitAndFinishGame() {
		// it will be overridden
	}

	protected void showHideAds() {

		// it will be overridden
	}

	// Listener that's called when we finish querying the items and
	// subscriptions we own
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result,
				Inventory inventory) {
			Log.d(TAG, "Query inventory finished.");
			if (result.isFailure()) {
				complain("Failed to query inventory: " + result);
				return;
			}

			Log.d(TAG, "Query inventory was successful.");

			/*
			 * Check for items we own. Notice that for each purchase, we check
			 * the developer payload to see if it's correct! See
			 * verifyDeveloperPayload().
			 */

			// Check for gas delivery -- if we own gas, we should fill up the
			// tank immediately

			if (inventory.hasPurchase(getResources().getString(
					R.string.inapp_purchase_key))) {

				SharedPrefUtils.putHasPurchased(
						AppsPartanBaseGameActivity.this, true);
				showHideAds();

			} else {

				Purchase removeAdsPurchase = inventory
						.getPurchase(getResources().getString(
								R.string.inapp_purchase_key));

				if (removeAdsPurchase != null
						&& verifyDeveloperPayload(removeAdsPurchase)) {
					System.out
							.println("Kp inapp : User has already purchased this item for removing ads. ");
					Log.d(TAG,
							"User has already purchased this item for removing ads. Write the Logic for removign Ads.");
					mHelper.consumeAsync(
							inventory.getPurchase(getResources().getString(
									R.string.inapp_purchase_key)),
							mConsumeFinishedListener);
					return;
				}

				Log.d(TAG,
						"Initial inventory query finished; enabling main UI.");
			}
		}

	};

	public void makeInAppPurchase() {
		Log.d(TAG, "Buy gas button clicked.");

		/*
		 * TODO: for security, generate your payload here for verification. See
		 * the comments on verifyDeveloperPayload() for more info. Since this is
		 * a SAMPLE, we just use an empty string, but on a production app you
		 * should carefully generate this.
		 */
		String payload = "";

		mHelper.launchPurchaseFlow(this,
				getResources().getString(R.string.inapp_purchase_key), 10000,
				mPurchaseFinishedListener, payload);
	}

	private void setUpInapp() {

		mHelper = new IabHelper(this, getResources().getString(
				R.string.base64EncodedPublicKey));

		// enable debug logging (for a production application, you should set
		// this to false).
		mHelper.enableDebugLogging(true);

		// Start setup. This is asynchronous and the specified listener
		// will be called once setup completes.
		Log.d(TAG, "Starting setup.");
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					complain("Problem setting up in-app billing: " + result);
					return;
				}

				// Hooray, IAB is fully set up. Now, let's get an inventory of
				// stuff we own.
				Log.d(TAG, "Setup successful. Querying inventory.");
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});

	}

	public void restoreInAppTransaction() {

		mHelper.queryInventoryAsync(mGotInventoryListenerRestorePurchase);

	}

	IabHelper.QueryInventoryFinishedListener mGotInventoryListenerRestorePurchase = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result,
				Inventory inventory) {

			if (result.isFailure()) {
				// handle error here
			} else {
				isItemAlreadyPurchased(inventory);
			}
		}
	};

	public void isItemAlreadyPurchased(Inventory inventory) {

		boolean is_purchased_1 = inventory.hasPurchase(getResources()
				.getString(R.string.inapp_purchase_key));

		if (is_purchased_1) {

			Toast.makeText(AppsPartanBaseGameActivity.this,
					"restore purchase suceefully", Toast.LENGTH_LONG).show();

			SharedPrefUtils.putHasPurchased(AppsPartanBaseGameActivity.this,
					true);
			showHideAds();

		} else {

			Toast.makeText(AppsPartanBaseGameActivity.this,
					"Please  purchase item first", Toast.LENGTH_LONG).show();

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","
				+ data);

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.d(TAG, "onActivityResult handled by IABUtil.");
		}
	}

	// Callback for when a purchase is finished
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			Log.d(TAG, "Purchase finished: " + result + ", purchase: "
					+ purchase);
			if (result.isFailure()) {
				complain("Error purchasing: " + result);
				return;
			}
			if (!verifyDeveloperPayload(purchase)) {
				complain("Error purchasing. Authenticity verification failed.");
				return;
			}

			Log.d(TAG, "Purchase successful.");

			if (purchase.getSku().equals(
					getResources().getString(R.string.inapp_purchase_key))) {
				// bought 1/4 tank of gas. So consume it.
				Log.d(TAG,
						"removeAdsPurchase was succesful.. starting consumption.");
				mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			}
		}
	};

	// Called when consumption is complete
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			Log.d(TAG, "Consumption finished. Purchase: " + purchase
					+ ", result: " + result);

			// We know this is the "gas" sku because it's the only one we
			// consume,
			// so we don't check which sku was consumed. If you have more than
			// one
			// sku, you probably should check...
			if (result.isSuccess()) {

				SharedPrefUtils.putHasPurchased(
						AppsPartanBaseGameActivity.this, true);

				showHideAds();

				// successfully consumed, so we apply the effects of the item in
				// our
				// game world's logic, which in our case means filling the gas
				// tank a bit
				Log.d(TAG, "Consumption successful. Provisioning.");
				System.out
						.println("Kp inapp : You have purchased for removing ads from your app. ");
				alert("You have purchased for removing ads from your app.");
			} else {
				complain("Error while consuming: " + result);
			}
			Log.d(TAG, "End consumption flow.");
		}
	};

	/** Verifies the developer payload of a purchase. */
	boolean verifyDeveloperPayload(Purchase p) {
		String payload = p.getDeveloperPayload();

		/*
		 * TODO: verify that the developer payload of the purchase is correct.
		 * It will be the same one that you sent when initiating the purchase.
		 * 
		 * WARNING: Locally generating a random string when starting a purchase
		 * and verifying it here might seem like a good approach, but this will
		 * fail in the case where the user purchases an item on one device and
		 * then uses your app on a different device, because on the other device
		 * you will not have access to the random string you originally
		 * generated.
		 * 
		 * So a good developer payload has these characteristics:
		 * 
		 * 1. If two different users purchase an item, the payload is different
		 * between them, so that one user's purchase can't be replayed to
		 * another user.
		 * 
		 * 2. The payload must be such that you can verify it even when the app
		 * wasn't the one who initiated the purchase flow (so that items
		 * purchased by the user on one device work on other devices owned by
		 * the user).
		 * 
		 * Using your own server to store and verify developer payloads across
		 * app installations is recommended.
		 */

		return true;
	}

	void complain(String message) {
		Log.e(TAG, "**** IN APP Purchase Error: " + message);
		alert(message);
	}

	void alert(String message) {
		Log.d(TAG, "Showing alert dialog: " + message);
		// TextView resultTv = (TextView) findViewById(R.id.textView_result);
		// resultTv.setText("Result : " + message);
	}

	public MainMenuScene getMainMenuScene() {
		return mainMenuScene;
	}

	public AlphabetPaintingScene alphabetPaintingScene;

	public AlphabetPaintingScene getAlphabetPaintingScene() {
		return alphabetPaintingScene;
	}

	@Override
	public Engine onLoadEngine() {

		Engine engine = EngineUtilits.onLoadEngine(this);

		return engine;
	}

	@Override
	public void onLoadResources() {

		SceneManager.init(this);

		mainMenuScene = null;

		mainMenuScene = MainMenuScene.getMainMenuScene(this);

		alphabetPaintingScene = null;
		alphabetPaintingScene = AlphabetPaintingScene
				.getAlphabetPaintingScene(this);

	}

	@Override
	public Scene onLoadScene() {


		return mainMenuScene.getScene();
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

}
