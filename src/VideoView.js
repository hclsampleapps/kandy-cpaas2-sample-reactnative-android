
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */
import React, { Component } from 'react';

import { DeviceEventEmitter, ViewPropTypes, requireNativeComponent } from 'react-native';
import { StyleSheet, Button, TextInput, PermissionsAndroid, View } from 'react-native';
import { NativeModules } from 'react-native';
import PropTypes from 'prop-types';
var viewProps = {
  name: 'VideoView',
  propTypes: {
    url: PropTypes.string,
    ViewPropTypes,
  }
}
module.exports = requireNativeComponent('VideoView', viewProps);