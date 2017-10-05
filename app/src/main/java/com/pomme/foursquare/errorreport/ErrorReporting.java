package com.pomme.foursquare.errorreport;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.BuildConfig;
import com.crashlytics.android.answers.CustomEvent;

/**
 * Created by pomme on 2017-10-05.
 */

public class ErrorReporting {

    public enum ErrorType {
        FOURSQUARE_ERROR,
        NETWORK_FAILURE,
        EXCEPTION,
        THROWABLE,
        DETAIL_ERROR
    }

    public static void sendErrorMessageToFabric(ErrorType errorType, String error){
        if (error != null && !BuildConfig.DEBUG){
            Answers.getInstance().logCustom(new CustomEvent(errorType.name())
                    .putCustomAttribute("error message", error));
        }
    }

}
