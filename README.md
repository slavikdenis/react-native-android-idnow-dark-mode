# React Native (Android) - IDnow Dark Mode problem

Example repo for showcasing dark mode problem with React Native and IDNow VideoIdent SDK (Android) 

## How to reproduce the error

- [Setting up the development environment - React Native CLI](https://reactnative.dev/docs/environment-setup)
- clone repository
- install NodeJS & yarn package manager
- run command `cd ReactNativeIDnow/`
- run command `yarn install`
- fill `IDNOW_*` constants in `App.tsx` accordingly
- run command `yarn run android` on Android device with Dark Mode on

## Problem description

IDnow SDK for Android enforces dark mode based on device settings even when app is not supporting dark mode, which causing inconsistent broken layout. See image below.


![Broken colors](https://raw.githubusercontent.com/slavikdenis/react-native-android-idnow-dark-mode/main/idnow-android-forced-dark-mode.jpeg "IDnow broken colors")