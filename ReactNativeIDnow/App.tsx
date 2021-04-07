/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import React from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  View,
  NativeModules,
  Platform,
  Button,
} from 'react-native';
const {IDnowModule} = NativeModules;

if (Platform.OS !== 'android') {
  throw new Error('Only Android example!');
}

// Fill accordingly
const IDNOW_COMPANY_ID = '';
const IDNOW_TRANSACTION_TOKEN = '';

const App = () => {
  const Colors = {
    white: '#FFFFFF',
  };

  const backgroundStyle = {
    flex: 1,
    padding: 10,
    backgroundColor: Colors.white,
  };

  const startVideoIdent = async () => {
    try {
      await IDnowModule.startVideoIdent({
        companyId: IDNOW_COMPANY_ID,
        transactionToken: IDNOW_TRANSACTION_TOKEN,
        showVideoOverviewCheck: true,
        userInterfaceLanguage: 'en',
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar barStyle="dark-content" />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <View style={{backgroundColor: Colors.white}}>
          <Button onPress={startVideoIdent} title="Start VideoIdent" />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

export default App;
