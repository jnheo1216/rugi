const path = require('path');

module.exports = {
  devServer: {
    port: 8082
  },
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.join(__dirname, 'src/')
      }
    }
  }
};
