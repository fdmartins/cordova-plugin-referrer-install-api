cordova-plugin-referrer-install-api
=======================


# Installing

This plugin follows the Cordova 3.0+ plugin spec, so it can be installed through the Cordova CLI in your existing Cordova project:
```bash
cordova plugin add 
```

add de dependence installreferrer in your build-extra.gradle.

dependencies {
    implementation 'com.android.installreferrer:installreferrer:1.1'
}


# Use

```js
//In your 'deviceready' handler, set up your Analytics tracker:
window.referrerinstall.getReferrer(
  function(success){
    //anything
}
, function(error){
    //anything
});

