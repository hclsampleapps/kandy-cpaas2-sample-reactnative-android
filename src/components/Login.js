import React, { Component } from 'react';
import { Platform, Alert,TouchableOpacity, StyleSheet, Text, View, TextInput, Button, ScrollView } from 'react-native';
import { ToastAndroid } from 'react-native';

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = { baseurl: 'oauth-cpaas.att.com', username: 'ashishgoel35@gmail.com', password: 'Test@123', clientId: 'PUB-kandy.j6z8' };
    }


    onLoginPress = () => {

        if (this.state.baseurl.trim() == '') {
            ToastAndroid.show('Please Enter base url', ToastAndroid.SHORT);
            return;
        } else if (this.state.username.trim() == '') {
            ToastAndroid.show('Please Enter username', ToastAndroid.SHORT);
            return;
        } else if (this.state.password.trim() == '') {
            ToastAndroid.show('Please Enter password', ToastAndroid.SHORT);
            return;
        } else if (this.state.clientId.trim() == '') {
            ToastAndroid.show('Please Enter password', ToastAndroid.SHORT);
            return;
        }

        this.props.onLoginForm(this.state);
    }

    render() {
        return (
            <ScrollView style={styles.container} keyboardShouldPersistTaps = 'always'>
                <Text
                    style={{ fontSize: 27, flex: 1 }}>
                    Login
                </Text>
                <TextInput style={styles.textStyle} placeholder='Baseurl' defaultValue={this.state.baseurl} onChangeText={(value) => this.setState({ baseurl: value })} />
                <TextInput style={styles.textStyle} placeholder='Username' defaultValue={this.state.username} onChangeText={(value) => this.setState({ username: value })} />
                <TextInput style={styles.textStyle} placeholder='Password' defaultValue={this.state.password} secureTextEntry={true} onChangeText={(value) => this.setState({ password: value })} />
                <TextInput style={styles.textStyle} placeholder='ClientId' defaultValue={this.state.clientId} onChangeText={(value) => this.setState({ clientId: value })} />
                <View style={{ margin: 7 }} />
                <TouchableOpacity
                    style={styles.button}
                    onPress={this.onLoginPress}
                >
                    <Text style={{color:'white'}}> Submit </Text>
                </TouchableOpacity>
                {/* <Button
                    onPress={this.onLoginPress}
                    title="Submit"
                /> */}
            </ScrollView>
        );

    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        width: '100%'
    },
    textStyle: {
        borderBottomColor: '#333',
        borderBottomWidth: 1
    },
    button: {
        alignItems: 'center',
        backgroundColor: 'blue',
        padding: 10
    },
});
