## FOOD NEAR KOOLICAR APP

This android application accesses the Foursquare api and returns 10 food venues close to
the Koolicar Montreal office. Further information about a venue can be found by
clicking on it.

To compile this project:

- Obtain a client id and client secret from [Foursquare](https://developer-test.foursquare.com/docs)
and add to string resources with the ids "foursquareClientSecret" and "foursquareClientSecret"
- Obtain an api key and secret from [Fabric](https://docs.fabric.io/android/fabric/settings/api-keys.html). Add a fabric.properties file in the root of your project with

 - apiKey = {yourApiKey}

 - apiSecret = {yourApiSecret}


This application makes use of RXJava2, [Dagger2-Android](https://google.github.io/dagger/android.html),
Butterknife and Retrofit.
