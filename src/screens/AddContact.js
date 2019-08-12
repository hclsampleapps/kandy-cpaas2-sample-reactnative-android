import React, { Component } from 'react';
import { StyleSheet, View ,TextInput, Button,ToastAndroid} from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import KandyCpassLib from '../../KandyCpassLib';

export default class AddContact extends Component {

    static navigationOptions = {
        title: 'Add Contact'
    }

    constructor(props) {
        super(props);
        this.state = {primaryContact:"test@test.in",firstName:"Test First",lastName:"Test Last",emailAddress:"test@test.in",businessPhoneNumber:"+1427888890",homePhoneNumber:"+1427888890",mobilePhoneNumber:"+1427888890",buttonName:"Add Contact",buddy:true,isLoading: false,task:"add",contactId:""};
    }

    addUpdateContact = () => {
        console.log("Add Contact");

        var contactData = {
            primaryContact:this.state.primaryContact,
            firstName:this.state.firstName,
            lastName:this.state.lastName,
            email:this.state.emailAddress,
            businessPhoneNumber:this.state.businessPhoneNumber,
            homePhoneNumber:this.state.homePhoneNumber,
            mobilePhoneNumber:this.state.mobilePhoneNumber,
            contactId:this.state.contactId
        }
        this.setState({ isLoading: true });
        console.log("contact string",JSON.stringify(contactData));

        if(this.state.task == "add"){
            KandyCpassLib.addContact(JSON.stringify(contactData))
            .then((response) => {
                this.setState({ isLoading: false });
                console.log("Add contact response", response);
                ToastAndroid.show(response, ToastAndroid.SHORT);
            })
            .catch((err) => {
                console.log("error", err)
                this.setState({ isLoading: false });
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })
        }else{
            KandyCpassLib.updateContact(JSON.stringify(contactData))
            .then((response) => {
                this.setState({ isLoading: false });
                console.log("Update contact response", response);
                ToastAndroid.show(response, ToastAndroid.SHORT);
            })
            .catch((err) => {
                console.log("error", err)
                this.setState({ isLoading: false });
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })
        }

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

    componentWillMount() {

        const { navigation } = this.props;
        const task = navigation.getParam('task', 'No-Task');
        if(task == "addContact"){
            this.setState({buttonName:"Add Contact"});
            this.setState({task:"add"});
        }else if(task == "updateContact"){
            this.setState({buttonName:"Update Contact"});
            this.setState({task:"update"});
            const response = navigation.getParam('otherParam', 'No-Task');
            console.log("Update Contact",response);
            this.setState({contactId:response.contactId,
                primaryContact:response.attributeList.attributeMap.primaryContact,
                firstName:response.attributeList.attributeMap.firstName,
                lastName:response.attributeList.attributeMap.lastName,
                emailAddress:response.attributeList.attributeMap.emailAddress,
                businessPhoneNumber:response.attributeList.attributeMap.businessPhoneNumber,
                homePhoneNumber:response.attributeList.attributeMap.homePhoneNumber,
                mobilePhoneNumber:response.attributeList.attributeMap.mobile});
        }

    }

    render() {
        return (
            <View style={styles.container}>
                {this.renderProgressbar()}
                <TextInput style={styles.textStyle} placeholder='Enter Primary Contact' defaultValue={this.state.primaryContact} onChangeText={(value) => this.setState({primaryContact:value})} />
                <TextInput style={styles.textStyle} placeholder='Enter First Name' defaultValue={this.state.firstName} onChangeText={(value) => this.setState({firstName:value})} />
                <TextInput style={styles.textStyle} placeholder='Enter Last Name' defaultValue={this.state.lastName} onChangeText={(value) => this.setState({lastName:value})} />
                <TextInput style={styles.textStyle} placeholder='Enter Email Address' defaultValue={this.state.emailAddress} onChangeText={(value) => this.setState({emailAddress:value})} />
                <TextInput style={styles.textStyle} placeholder='Enter Business Phone Number' defaultValue={this.state.businessPhoneNumber} onChangeText={(value) => this.setState({businessPhoneNumber:value})} />
                <TextInput style={styles.textStyle} placeholder='Enter Home Phone Number' defaultValue={this.state.homePhoneNumber} onChangeText={(value) => this.setState({homePhoneNumber:value})} />
                <TextInput style={styles.textStyle} placeholder='Enter Mobile Phone Number' defaultValue={this.state.mobilePhoneNumber} onChangeText={(value) => this.setState({mobilePhoneNumber:value})} />

                <Button
                    onPress={this.addUpdateContact}
                    title={this.state.buttonName}
                />
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#F5F5F5'
    },
    textStyle:{
        borderBottomColor: '#333',
        borderBottomWidth: 1 
    },
    spinnerTextStyle: {
        color: '#0000ff'
    }
});
