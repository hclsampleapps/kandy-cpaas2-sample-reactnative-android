import React, { Component } from 'react';

import {

  StyleSheet,

  View,
  Text,

  NativeModules,


  TouchableOpacity,
  DeviceEventEmitter,
  FlatList,
  ActivityIndicator
} from 'react-native';
const { AddressBookModule } = NativeModules;

class AddressBook extends React.Component {
  static navigationOptions = {
    title: 'AddressBook',
  };
  constructor(props) {
    super(props);
    contactList: []
    DeviceEventEmitter.addListener('AddressBookReceived', (event) => this.onAddressBook(event));

  }
  onAddressBook = (event) => {
    alert('Chat Received Successfully!');
  };

  state = {
    destinationId: '',
    messageText: '',
    contactList: []
  }

  showLoader = () => { this.setState({ showLoader: true }); };
  hideLoader = () => { this.setState({ showLoader: false }); };

  componentDidMount() {
    this.showLoader();
    AddressBookModule.initAddressBookModule((error, response) => {
      console.log(response);
      if (error == 'Success') {
        this.setState({ contactList: JSON.parse(response) });
        console.log(response);
        this.hideLoader();
        console.log("Address module initialize" + response);
      } else {
        console.log("Address module error");
      }
    });
  }

  componentWillUnmount() {
    this.state.contactList.length = 0
  }

  FlatListItemSeparator = () => {
    return (
      <View
        style={{
          height: 1,
          width: "100%",
          backgroundColor: "#000",
        }}
      />
    );
  }

  actionOnRow(item) {
    console.log('Selected Item :', item);
    this.props.navigation.push('UpdateContact', {
      contactData: item
    })
    this.props.navigation.navigate('UpdateContact')
  }

  render() {
    return (
      <View>
        <View style={{ position: 'absolute', top: "100%", right: 0, left: 0 }}>
          <ActivityIndicator animating={this.state.showLoader} size="large" color="grey" />
        </View>
        <FlatList
          data={this.state.contactList}
          keyExtractor={(item, index) => index.toString()}
          ItemSeparatorComponent={this.FlatListItemSeparator}
          renderItem={({ item }) => (
            <TouchableOpacity onPress={() => this.actionOnRow(item)}>
              <View>
                <View style={{ flexDirection: 'row' }}>
                  <Text style={styles.cell_text}>{"First Name : "+item.firstName}</Text>
                </View>
              </View>
              <View>
                <View style={{ flexDirection: 'row' }}>
                  <Text style={styles.cell_text}>{"Email : "+ item.email}</Text>
                </View>
              </View>
            </TouchableOpacity>
          )}

        />

      </View>
    );
  }
}

const styles = StyleSheet.create({
  cell_text: {
    marginTop: 10,
    marginBottom: 20,
    marginLeft: 20,
    fontSize: 12
  },
});

export default AddressBook; 