import React, { Component } from 'react';
import { StyleSheet, View, Image, TouchableOpacity, ToastAndroid, FlatList, AppState } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import ContactView from '../components/ContactView';
import KandyCpassLib from '../../KandyCpassLib';
import { NavigationEvents } from 'react-navigation';


export default class AddressBook extends Component {

    static navigationOptions = {
        title: 'Address Book'
    }

    constructor(props) {
        super(props);
        this.state = { isLoading: false, contactList: [],check:true };

    }

    componentDidMount() {
        console.log("Component did mount");

    }

    componentWillMount() {
        console.log("Component will mount");
        this.initAddressService();
    }


    initAddressService = () => {

        KandyCpassLib.initaddressService()
            .then((response) => {
                console.log("response", response);
            })
            .catch((err) => {
                console.log("error", err)
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })

        this.getContactList();
    }

    getContactList = () => {

        this.setState({ isLoading: true });

        KandyCpassLib.getContactList()
            .then((response) => {
                this.setState({ isLoading: false });
                var responseParse = JSON.parse(response);
                this.setState({ contactList: responseParse });
                //console.log("Contact List response", response);
                //console.log("Contact List response", responseParse);
            })
            .catch((err) => {
                console.log("error", err)
                this.setState({ isLoading: false });
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })
    }

    addContact = () => {
        this.setState({ check: false });
        this.props.navigation.navigate('AddContact', {
            task: "addContact",
            otherParam: 'anything you want here',
        });

    }

    _onPressItem = (item) => {
        //console.log("pressItem",item)
        this.setState({ check: false });
        this.props.navigation.navigate('AddContact', {
            task: "updateContact",
            otherParam: item,
        });
    }

    _onDeleteContact = (contactId) => {
        //console.log("delete contact",contactId)
        this.setState({ isLoading: true });
        KandyCpassLib.deleteContact(contactId)
            .then((response) => {
                this.setState({ isLoading: false });
                console.log(response, ToastAndroid.SHORT);
                this.getContactList();
            })
            .catch((err) => {
                console.log("error", err)
                this.setState({ isLoading: false });
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })
    }

    MyScreen = () => {
        return (
            <NavigationEvents
                onWillFocus={payload => console.log('will focus', payload)}
                onDidFocus={(payload) => {
                    console.log('did focus', payload)
                    if(!this.state.check){
                        this.getContactList();
                        this.setState({ check: true });
                    }
                    
                }}
                onWillBlur={payload => console.log('will blur', payload)}
                onDidBlur={payload => console.log('did blur', payload)}
            />
        );
    }

    renderProgressbar() {

        return (
            <Spinner
                visible={this.state.isLoading}
                textContent={'Loading...'}
                textStyle={styles.spinnerTextStyle}
            />
        );
    }

    FlatListItemSeparator = () => {
        return (
            <View style={{ height: 1, width: "100%", backgroundColor: "#607D8B" }} />
        );
    };

    _renderItem = ({ item }) => {
        console.log("item ", item);
        console.log("Length", this.state.contactList.length);
        if (this.state.contactList.length > 0) {
            return (
                <ContactView
                    data={item}
                    title={item.attributeList.attributeMap.emailAddress}
                    contactId={item.contactId}
                    onPressItem={this._onPressItem}
                    onDeleteItem={this._onDeleteContact}
                />
            )
        }

    }


    render() {
        return (
            <View style={styles.container}>
                {this.renderProgressbar()}
                {this.MyScreen()}
                <FlatList
                    data={this.state.contactList}
                    ItemSeparatorComponent={this.FlatListItemSeparator}
                    renderItem={this._renderItem}
                />

                <TouchableOpacity activeOpacity={0.5} onPress={this.addContact} style={styles.TouchableOpacityStyle} >

                    <Image source={{ uri: 'https://reactnativecode.com/wp-content/uploads/2017/11/Floating_Button.png' }}

                        style={styles.FloatingButtonStyle} />

                </TouchableOpacity>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        backgroundColor: '#F5F5F5'
    },
    TouchableOpacityStyle: {

        position: 'absolute',
        width: 50,
        height: 50,
        alignItems: 'center',
        justifyContent: 'center',
        right: 30,
        bottom: 30,
    },
    FloatingButtonStyle: {

        resizeMode: 'contain',
        width: 50,
        height: 50,
    },
    listContainer: {
        flex: 1,
        flexDirection: 'row',
        padding: 10
    },
    spinnerTextStyle: {
        color: '#0000ff'
    }
});
