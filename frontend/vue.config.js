module.exports = {
  lintOnSave: false,
  devServer: {
    client: {
      overlay: {
        warnings: false,
        errors: true
      }
    },
    port: 8080,
    hot: true
  }
} 