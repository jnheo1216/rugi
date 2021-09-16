const path = require('path');

module.exports = {
  devServer: {
    port: 80
  },
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.join(__dirname, 'src/')
      }
    }
  }
};
