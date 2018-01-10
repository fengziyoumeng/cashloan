var webpack = require('webpack');
var path = require('path');
var uglifyJsPlugin = webpack.optimize.UglifyJsPlugin;
var CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  entry: [
    path.resolve(__dirname, 'react/main.js'),
  ],
  output: {
    path: __dirname + '/webapp/build',
    publicPath: '/build/',
    filename: './bundle.js',
    chunkFilename: "[id].chunk.js"
  },
  module: {
    loaders: [{
      test: /\.css$/,
      loader: 'style-loader!css-loader'
    }, {
      test: /\.js[x]?$/,
      include: path.resolve(__dirname, 'react'),
      loader: 'babel',
      query: {compact: false} 
    }, {
      test: /\.(png|jpg)$/,
      loader: 'url-loader?limit=8192'
    }, {
      test: /\.(woff|woff2|eot|ttf|svg)(\?.*$|$)/,
      loader: 'url'
    }]
  },
  resolve: {
    extensions: ['', '.js', '.jsx'],
  },
  plugins: [
    new webpack.optimize.DedupePlugin(),
    new uglifyJsPlugin({
      compress: {
        warnings: false
      },
      output: {
        comments: false,
      }
    }),
    new CopyWebpackPlugin([{
      from: './webapp/dev/index.css',
      to: 'index.css'
    }, {
      from: './webapp/dev/fonts',
      to: 'fonts'
    }, {
      from: './webapp/dev/images',
      to: 'images'
    }]),
  ]
};