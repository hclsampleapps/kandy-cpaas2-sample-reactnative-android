import React from 'react';
import { StyleSheet, Text, View, FlatList, Dimensions , TouchableWithoutFeedback} from 'react-native';

const data = [
  { key: 'SMS' }, { key: 'Chat' }, { key: 'AddressBook' } , { key: 'Directory' }
];

const formatData = (data, numColumns) => {
  const numberOfFullRows = Math.floor(data.length / numColumns);
  let numberOfElementsLastRow = data.length - (numberOfFullRows * numColumns);
  while (numberOfElementsLastRow !== numColumns && numberOfElementsLastRow !== 0) {
    data.push({ key: `blank-${numberOfElementsLastRow}`, empty: true });
    numberOfElementsLastRow++;
  }
  return data;
};

const numColumns = 2;

class DashBoard extends React.Component {

  onPressAction = (text) => {
    switch(text) {
      case 'SMS':
        this.props.navigation.navigate('SMS')
        break;
      
      case 'Chat':
        this.props.navigation.navigate('Chat')
        break;
 
      case 'AddressBook':
        this.props.navigation.navigate('AddressBook')
        break;  

     case 'Directory':
          this.props.navigation.navigate('Directory')
          break;  
  
      default:
        Alert.alert("Wrong Choice");
      }
  }

   renderItem = ({ item, index }) => {
    if (item.empty === true) {
      return <View style={[styles.item, styles.itemInvisible]} />;
    }
    return (
      <TouchableWithoutFeedback onPress={(event)=>this.onPressAction(item.key)}>
      <View
        style={styles.item}
      >
        <Text style={styles.itemText}>{item.key}</Text>
      </View>
      </TouchableWithoutFeedback>
    );
  };

  render() {
    return (
      <FlatList
        data={formatData(data, numColumns)}
        style={styles.container}
        renderItem={this.renderItem}
        numColumns={numColumns}
      />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  item: {
    backgroundColor: '#696969',
    alignItems: 'center',
    justifyContent: 'center',
    flex: 1,
    margin: 1,
    height: Dimensions.get('window').width / numColumns,
  },
  itemInvisible: {
    backgroundColor: 'transparent',
    fontSize: 10
  },
  itemText: {
    color: '#fff',
    fontSize: 20
  },
});

export default DashBoard; 