function ReferrerInstallApi() {}


ReferrerInstallApi.prototype.getReferrer = function( success, error) {
  cordova.exec(success, error, 'ReferrerInstallApiPlugin', 'getReferrer', []);
};

module.exports = new ReferrerInstallApi();
