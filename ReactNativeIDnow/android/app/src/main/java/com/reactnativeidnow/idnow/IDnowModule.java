package ua.fairo.idnow;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;

import de.idnow.sdk.IDnowSDK;

public class IDnowModule extends ReactContextBaseJavaModule {

  private static final String TAG = "IDnowModule";
  private final ReactApplicationContext reactContext;
  private Promise idnowPromise;

  private final ActivityEventListener idnowActivityEventListener = new BaseActivityEventListener() {

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
      if (requestCode == IDnowSDK.REQUEST_ID_NOW_SDK) {
        if (resultCode == IDnowSDK.RESULT_CODE_SUCCESS) {
          if (data != null) {
            String transactionToken = data.getStringExtra(IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN);
            Log.v(TAG, "success, transaction token: " + transactionToken);
            idnowPromise.resolve(true);
          }
        } else if (resultCode == IDnowSDK.RESULT_CODE_CANCEL) {
          if (data != null) {
            String transactionToken = data.getStringExtra(IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN);
            String errorMessage = data.getStringExtra(IDnowSDK.RESULT_DATA_ERROR);
            Log.v(TAG, "canceled, transaction token: " + transactionToken + ", error: " + errorMessage);
            idnowPromise.reject("CANCELLED", errorMessage);
          }
        } else if (resultCode == IDnowSDK.RESULT_CODE_FAILED) {
          if (data != null) {
            String transactionToken = data.getStringExtra(IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN);
            String errorMessage = data.getStringExtra(IDnowSDK.RESULT_DATA_ERROR);
            Log.v(TAG, "failed, transaction token: " + transactionToken + ", error: " + errorMessage);
            idnowPromise.reject("FAILED", errorMessage);
          }
        } else {
          Log.v(TAG, "Result Code: " + resultCode);
          idnowPromise.reject("INTERNAL_ERROR", "Internal error: " + resultCode);
        }
      }

    }
  };

  IDnowModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(idnowActivityEventListener);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "IDnowModule";
  }

  @ReactMethod
  public void startVideoIdent(final ReadableMap options, final Promise promise) {
    Activity currentActivity = getCurrentActivity();
    idnowPromise = promise;

    String companyId = options.getString("companyId");
    String userInterfaceLanguage = options.getString("userInterfaceLanguage");
    String transactionToken = options.getString("transactionToken");
    Boolean showVideoOverviewCheck = options.getBoolean("showVideoOverviewCheck");

    Log.d(
      TAG,
      "Starting Video Ident, companyId: " + companyId +
        ", userInterfaceLanguage: " + userInterfaceLanguage +
        ", transactionToken: " + transactionToken
    );

    try {
      IDnowSDK.getInstance().initialize(currentActivity, companyId, userInterfaceLanguage);

      IDnowSDK.setTransactionToken(transactionToken, reactContext);
      IDnowSDK.setShowVideoOverviewCheck(showVideoOverviewCheck, reactContext);
      IDnowSDK.setShowErrorSuccessScreen(false, reactContext);

      IDnowSDK.getInstance().start(IDnowSDK.getTransactionToken(reactContext));
    } catch (Exception e) {
      e.printStackTrace();
      String errorMessage = e.getMessage();
      Log.v(TAG, "failed, while starting video ident " + errorMessage);
      promise.reject("ERR_UNEXPECTED_EXCEPTION", errorMessage);
    }
  }
}
