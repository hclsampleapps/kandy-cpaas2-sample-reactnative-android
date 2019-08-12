import React, { Component } from 'react';
import { ScrollView, LayoutAnimation, StyleSheet, View, TextInput, Picker, Image, TouchableOpacity, ToastAndroid, Modal, Button, Text, Platform, UIManager } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import KandyCpassLib from '../../KandyCpassLib';
import Expandable_ListView from '../components/Expandable_ListView';

export default class Presence extends Component {


    static navigationOptions = {
        title: 'Presence'
    }

    constructor(props) {
        super(props);

        this.state = {
            isLoading: false, statusModal: false, addPresenceModal: false, otherText: '', activity: 'Available', selectedActivity: 'Available', presenceName: '', status: 'Open',
            statusItems: ['Available', 'Away', 'Busy', 'Lunch', 'On the phone', 'Vacation', 'Other', 'Unknown'],
            availablityItems: ['Open', 'Close'],
            presenceList: []
        };

        if (Platform.OS === 'android') {

            UIManager.setLayoutAnimationEnabledExperimental(true)

        }

    }

    setStatusModal(visible) {
        this.setState({ statusModal: visible });
    }

    setAddPresenceModal(visible) {
        this.setState({ addPresenceModal: visible });
    }

    componentWillMount() {
        console.log("Component will mount");
        this.initPresenceService();
    }

    initPresenceService = () => {

        KandyCpassLib.initPresenceService()
            .then((response) => {
                console.log("enum response", response);
                let statusItemsArray = JSON.parse(response);
               // this.setState({ statusItems: statusItemsArray });
            })
            .catch((err) => {
                console.log("error", err)
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })

        this.fetchAllPresenceList();

    }

    fetchAllPresenceList = () => {
        KandyCpassLib.fetchAllPresenceLists()
            .then((response) => {
                console.log("fetchAllPresenceLists", response);
                let presenceList = JSON.parse(response);
                for (p of presenceList) {
                    p.expanded = false;
                }

                this.setState({ presenceList: presenceList });

                console.log("updated fetchAllPresenceLists", JSON.stringify(presenceList));

            })
            .catch((err) => {
                console.log("error", err.message)
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })

        this.fetchPresenceByClientCorrelator();
    }

    fetchPresenceByClientCorrelator = () => {
        KandyCpassLib.fetchPresenceByClientCorrelator()
            .then((response) => {
                console.log("fetchPresenceByClientCorrelator", response);
                if (response == "create") {
                    this.createPresenceSource();
                } else {
                    this.setState({ selectedActivity: response });
                    this.setState({ activity: response });
                }

            })
            .catch((err) => {
                console.log("error", err.message)
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })
    }



    createPresenceSource = () => {
        KandyCpassLib.createPresenceSource()
            .then((response) => {
                console.log("createPresenceSource", response);
                this.setState({ selectedActivity: response });
                this.setState({ activity: response });
            })
            .catch((err) => {
                this.setState({ activity: "Offline" });
                console.log("error", err.message)
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })
    }

    addPresenceList = () => {
        this.setAddPresenceModal(false);
        var listName = this.state.presenceName;
        if (listName != '') {
            KandyCpassLib.createPresenceList(listName)
                .then((response) => {
                    ToastAndroid.show("Created Successfully", ToastAndroid.SHORT);
                    console.log("response", response);
                    this.fetchAllPresenceList();                   
                })
                .catch((err) => {
                    ToastAndroid.show(err.message, ToastAndroid.SHORT);
                    console.log("error", err.message);
                    
                })
        }

    }

    updatePresenceStatus = () => {
        this.setStatusModal(false);
        var activity = this.state.activity;
        var status = this.state.status;
        var other = this.state.otherText;
        if(activity == "Vacation"){
            activity = "ON_VACATION";
        }else if(activity == "On the phone"){
            activity = "ON_THE_PHONE";
        }
        KandyCpassLib.updatePresenceSource(activity, status, other)
            .then((response) => {
                console.log("updatePresenceSource", response);
                this.setState({ selectedActivity: response });
                this.setState({ activity: response });
            })
            .catch((err) => {
                console.log("error", err.message)
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })
    }

    update_Layout = (index) => {

        LayoutAnimation.configureNext(LayoutAnimation.Presets.easeInEaseOut);

        const array = this.state.presenceList;

        array[index]['expanded'] = !array[index]['expanded'];

        this.setState(() => {
            return {
                presenceList: array
            }
        });
    }


    render() {

        let statusItems = this.state.statusItems.map((s, i) => {
            return <Picker.Item key={i} value={s} label={s} />
        });

        let availablityItems = this.state.availablityItems.map((s, i) => {
            return <Picker.Item key={i} value={s} label={s} />
        });

        return (
            <View style={styles.container}>
                <View style={styles.statusContainer}>
                    <View style={{ flex: 7, flexDirection: 'row' }}>
                        <Text style={{ fontSize: 18, margin: 10, fontWeight: 'bold' }}>Status:</Text>
                        <Text style={{ fontSize: 18, margin: 10 }}>{this.state.selectedActivity}</Text>
                    </View>
                    <View style={{ flex: 3, margin: 10, }}>
                        <TouchableOpacity
                            onPress={() => {
                                this.setStatusModal(true);
                            }}>
                            <Text style={{ fontSize: 18 }}>Edit</Text>
                        </TouchableOpacity>
                    </View>

                </View>

                <View style={styles.listContainer}>
                    <ScrollView contentContainerStyle={{ paddingHorizontal: 10, paddingVertical: 5 }}>
                        {
                            this.state.presenceList.map((item, key) =>
                                (
                                    <Expandable_ListView key={item.getName} onClickFunction={this.update_Layout.bind(this, key)} item={item} />
                                ))
                        }
                    </ScrollView>

                </View>
                <Modal
                    transparent={true}
                    visible={this.state.statusModal}
                    onRequestClose={() => {
                        //Alert.alert('Modal has been closed.');
                        this.setStatusModal(!this.state.statusModal);
                    }}>

                    <View style={styles.modalView}>

                        <Text style={{ fontSize: 22, margin: 10, fontWeight: 'bold' }}>Update Status</Text>
                        <View style={{ flexDirection: 'row' }}>
                            <Text style={{ fontSize: 18, margin: 10, fontWeight: 'bold' }}>Status:</Text>
                            <Picker
                                selectedValue={this.state.activity}
                                style={{ height: 50, width: 200 }}
                                onValueChange={(itemValue, itemIndex) =>
                                    this.setState({ activity: itemValue })
                                }>
                                {statusItems}
                            </Picker>
                        </View>
                        <View style={{ margin: 10 }}>
                            <TextInput
                                style={{ height: 40, borderColor: 'gray', borderWidth: 1 }}
                                onChangeText={(text) => this.setState({ otherText: text })}
                                placeholder="Enter Message"
                                value={this.state.otherText}
                            />
                        </View>
                        <View style={{ flexDirection: 'row' }}>
                            <Text style={{ fontSize: 18, margin: 10, fontWeight: 'bold' }}>Availablty:</Text>
                            <Picker
                                selectedValue={this.state.status}
                                style={{ height: 50, width: 200 }}
                                onValueChange={(itemValue, itemIndex) =>
                                    this.setState({ status: itemValue })
                                }>
                                {availablityItems}
                            </Picker>
                        </View>
                        <View style={styles.buttonViewStyle}>

                            <Button style={{ margin: 10 }}
                                onPress={() => {
                                    this.setStatusModal(!this.state.statusModal);
                                }}
                                title="Cancel"
                                color="#841584"
                                accessibilityLabel="Learn more about this purple button"
                            />
                            <Button style={{ margin: 10 }}
                                onPress={() => {
                                    this.updatePresenceStatus();
                                }}
                                title="Ok"
                                color="#841584"
                                accessibilityLabel="Learn more about this purple button"
                            />
                        </View>

                    </View>
                </Modal>

                <Modal
                    transparent={true}
                    visible={this.state.addPresenceModal}
                    onRequestClose={() => {
                        //Alert.alert('Modal has been closed.');
                        this.setAddPresenceModal(!this.state.addPresenceModal);
                    }}>

                    <View style={styles.modalView}>

                        <Text style={{ fontSize: 22, margin: 10, fontWeight: 'bold' }}>New Presence List</Text>

                        <View style={{ margin: 10 }}>
                            <TextInput
                                style={{ height: 40, borderColor: 'gray', borderWidth: 1 }}
                                onChangeText={(text) => this.setState({ presenceName: text })}
                                placeholder="Enter the name"
                                value={this.state.presenceName}
                            />
                        </View>
                        <View style={styles.buttonViewStyle}>

                            <Button style={{ margin: 10 }}
                                onPress={() => {
                                    this.setAddPresenceModal(!this.state.addPresenceModal);
                                }}
                                title="Cancel"
                                color="#841584"
                                accessibilityLabel="Learn more about this purple button"
                            />
                            <Button style={{ margin: 10 }}
                                onPress={() => {
                                    this.addPresenceList()
                                }}
                                title="Ok"
                                color="#841584"
                                accessibilityLabel="Learn more about this purple button"
                            />
                        </View>

                    </View>
                </Modal>

                <TouchableOpacity activeOpacity={0.5} onPress={() => {
                    this.setAddPresenceModal(true);
                }} style={styles.TouchableOpacityStyle} >

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
        backgroundColor: '#F5F5F5'
    },
    statusContainer: {
        flex: 1,
        flexDirection: 'row'
    },
    listContainer: {
        flex: 4,
        justifyContent: 'flex-start',
        paddingTop: (Platform.OS === 'ios') ? 20 : 0,
        backgroundColor: '#F5FCFF',
    },
    modalView: {
        backgroundColor: 'white',
        width: '80%',
        height: '50%',
        alignSelf: 'center',
        marginTop: "20%",
        borderRadius: 3,
        justifyContent: 'center'
    },
    buttonViewStyle: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: "flex-end",
        marginLeft: "50%",
        marginRight: 20
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
    spinnerTextStyle: {
        color: '#0000ff'
    }
});
