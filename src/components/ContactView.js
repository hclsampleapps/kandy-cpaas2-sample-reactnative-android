import React, { Component } from 'react';
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';


export default class ContactView extends Component {

    constructor(props) {
        super(props);
    }

    _onPress = () => {
        this.props.onPressItem(this.props.data);
    };

    _onDelete = () => {
        this.props.onDeleteItem(this.props.contactId);
    }

    render() {
        return (
            <View style={styles.container}>
                <View style={{ flex: 0.9 }} >
                    <TouchableOpacity onPress={this._onPress}>
                        <Text>{this.props.title}</Text>
                    </TouchableOpacity>
                </View>

                <View style={{ flex: 0.1 }}>
                    <TouchableOpacity onPress={this._onDelete}>
                        <Icon name="md-trash" size={30} color="#000" />
                    </TouchableOpacity>
                </View>
            </View>

        );
    }

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        padding: 10
    }
});