## FOOD NEAR KOOLICAR

This android application accesses the Foursquare api and returns 10 food venues close to
the Koolicar Montreal office. Further information about a venue can be found by
clicking on it.

To compile this project:

- Obtain a client id and client secret from [Foursquare](https://developer-test.foursquare.com/docs)
and add to string resources with the ids "foursquareClientSecret" and "foursquareClientSecret"
- Obtain an api key and secret from [Fabric](https://docs.fabric.io/android/fabric/settings/api-keys.html). Add a fabric.properties file in your app directory with

 - apiKey = {yourApiKey}

 - apiSecret = {yourApiSecret}


This application makes use of RXJava2, [Dagger2-Android](https://google.github.io/dagger/android.html),
Butterknife and Retrofit. See [project dependency injection diagram](dependencyInjectionDiagram.png) to see how dependency injection is set-up.

Unidirectional data flow principles were incorporated into this app to help with state management of objects. See [unidirectional data flow diagram in project repository](unidirectionalDataFlow.png).
