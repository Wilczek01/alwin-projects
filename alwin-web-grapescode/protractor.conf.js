// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts

const {SpecReporter} = require('jasmine-spec-reporter');

exports.config = {
  allScriptsTimeout: 11000,
  specs: [
    './e2e/regular/**/*.e2e-spec.ts'
  ],
  capabilities: {
    browserName: 'chrome',
    // shardTestFiles: true,
    // maxInstances: 4,
    chromeOptions: {
      args: ["--headless", "--disable-gpu", "--window-size=1920x1080"],
      prefs: {
        protocol_handler: {
          excluded_schemes: {
            "callto": false
          }
        }
      }
    }
  },
  directConnect: true,
  baseUrl: 'http://localhost:4200/',
  framework: 'jasmine',
  jasmineNodeOpts: {
    showColors: true,
    defaultTimeoutInterval: 60000,
    print: function () {
    }
  },
  onPrepare: function () {
    require('ts-node').register({
      project: 'e2e/tsconfig.e2e.json'
    });
    jasmine.getEnv().addReporter(new SpecReporter({spec: {displayStacktrace: true}}));
  }
};
