import React, { Component, useState } from 'react';

import {
    StyleSheet,
    View,
    Text,
    NativeModules,
    Button,
    TouchableOpacity,
    ActivityIndicator,
    DeviceEventEmitter,
    ActionSheetIOS,
    FlatList
} from 'react-native';

const { PresenceModule } = NativeModules;

// var persenceManager = NativeModules.Persence;
// var persenceEvents = new NativeEventEmitter(NativeModules.Persence)

class Persence extends React.Component {
    static navigationOptions = {
        title: 'Persence',
    };

    state = {
        messageText: '',
        currentStatus: '',
        subscriberId: '',
        presenters: []
    }


    constructor(props) {
        super(props);
        presenters: []
        DeviceEventEmitter.addListener('PresenceReceived', (event) => this.onPresenceReceive(event));
    }

    onPresenceReceive = (event) => {
//        alert('Message Received Successfully!');
    };

    showLoader = () => { this.setState({ showLoader: true }); };
    hideLoader = () => { this.setState({ showLoader: false }); };

    openPicker = () => {
        ActionSheetIOS.showActionSheetWithOptions(
            {
                options: ["Cancel", "Available", "Unknown", "Other", "Away", "Busy", "Lunch", "OnThePhone", "Vacation"],
                cancelButtonIndex: 0
            },
            buttonIndex => {
                if (buttonIndex === 0) {

                }
                else if (buttonIndex === 1) {
                    this.state.currentStatus = "Available"
                    this.sendCurrentStatus(this.state.currentStatus)
                } else if (buttonIndex === 2) {
                    this.state.currentStatus = "Unknown"
                    this.sendCurrentStatus(this.state.currentStatus)
                } else if (buttonIndex === 3) {
                    this.state.currentStatus = "Other"
                    this.sendCurrentStatus(this.state.currentStatus)
                } else if (buttonIndex === 4) {
                    this.state.currentStatus = "Away"
                    this.sendCurrentStatus(this.state.currentStatus)
                } else if (buttonIndex === 5) {
                    this.state.currentStatus = "Busy"
                    this.sendCurrentStatus(this.state.currentStatus)
                } else if (buttonIndex === 6) {
                    this.state.currentStatus = "Lunch"
                    this.sendCurrentStatus(this.state.currentStatus)
                } else if (buttonIndex === 7) {
                    this.state.currentStatus = "OnThePhone"
                    this.sendCurrentStatus(this.state.currentStatus)
                } else if (buttonIndex === 8) {
                    this.state.currentStatus = "Vacation"
                    this.sendCurrentStatus(this.state.currentStatus)
                }
            }
        );
    }

    sendCurrentStatus = () => {
        this.setState({
            currentStatus: this.state.currentStatus
        })
        PresenceModule.setPersence(this.state.currentStatus, (error, message) => {
            if (error == 'Success') {
                alert('Persence Send Successfully!');
            } else {
                alert('Persence Sending failed!');
            }
        });
    }


    componentDidMount() {
        this.showLoader();
        PresenceModule.initPresenceModule((error, message) => {

            if (error == 'Success') {
                console.log("Presence module initialize");
                this.checkPersence();
            }
        });
    }

    componentWillUnmount() {
        this.state.presenters.length = 0
    }

    handleSubscriberId = (text) => {
        this.state.subscriberId = text;
    }

    checkPersence() {
        PresenceModule.getPersence( (error, response) => {
            if (error == 'Success') {
                this.setState({ presenters: JSON.parse(response) });
                this.hideLoader();
            } else {
                alert('Persence Sending failed!');
            }
        });
    }

    render() {
        return (
            <View>

                <View>
                    <Text style={styles.input}>
                        {this.state.currentStatus}
                    </Text>
                    <Button style={styles.buttonStyle}
                        title="Press here to set your status"
                        onPress={this.openPicker}
                    />
                </View>

                <View style={{ position: 'absolute', top: "50%", right: 0, left: 0 }}>
                    <ActivityIndicator animating={this.state.showLoader} size="large" color="grey" />
                </View>

                <View>
                    <Text style={styles.label_text}>
                        Contacts list with there status.
            </Text>
                </View>
                <FlatList
                    data={this.state.presenters}
                    keyExtractor={(item, index) => index.toString()}
                    renderItem={({ item }) => (
                        <TouchableOpacity>
                            <View>
                                <View style={{ flexDirection: 'row' }}>
                                    <Text style={styles.cell_text}>{item.userID}</Text>
                                </View>
                            </View>
                            <View>
                                <View style={{ flexDirection: 'row' }}>
                                    <Text style={styles.cell_text}>{item.activity}</Text>
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
    label_text: {
        marginTop: 10,
        marginBottom: 20,
        marginLeft: 20,
        fontSize: 20,
        fontWeight: 'bold'
    },
    Login_text: {
        marginTop: 0,
        padding: 20,
        fontSize: 17,
        fontWeight: 'normal'
    },
    input: {
        margin: 15,
        height: 40,
        borderColor: 'black',
        borderWidth: 1,
        borderRadius: 5,
        borderColor: 'gray'
    },
    buttonStyle: {
        marginTop: 20,
        padding: 20,
        borderRadius: 10,
        borderColor: 'black',
        flex: 60
    }
});

export default Persence; 