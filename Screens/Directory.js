// import React, { Component } from 'react';
// import {View,NativeModules,Alert,FlatList,TouchableOpacity,Text,StyleSheet} from 'react-native';
// import { Input, Button, SearchBar,CheckBox } from 'react-native-elements';

// var directoryManager = NativeModules.Directory;

// class Directory extends React.Component {
//     static navigationOptions = {
//       title: 'UpdateContact',
//     };
    
//     state = {
//         search: '',
//         contactList:[]
//     }

//     componentDidMount(){
//         directoryManager.initDirectoryModule((error, response) =>{
//             if(error == null) {
//                 console.log("Directory module initialize");
//             }
//       });
//     }

//     FlatListItemSeparator = () => {
//         return (
//           <View
//             style={{
//               height: 1,
//               width: "100%",
//               backgroundColor: "#000",
//             }}
//           />
//         );
//     }

//     updateSearch = search => {
//         this.setState({ search });
//         console.log(search)
//         var data = {
//             searchText: search
//         };
//         const myObjStr = JSON.stringify(data);
//         directoryManager.searchContactKeyWord(myObjStr,(error, response)=>{
//             if(error != null) {
//                  alert('Error in searching contact.');
//             } else {
//                 this.setState({ contactList: response });
//             }
//           });
//     };

//     actionOnRow(item) {
//         console.log('Selected Item :',item);
//     }

//     render() {

//       const { search } = this.state;

//       return (
//         <View>
//             <SearchBar
//             placeholder="Type Here..."
//             lightTheme="false"
//             onChangeText={this.updateSearch}
//             value={search}
//             />
//             <View>
//             <FlatList
//         data={this.state.contactList}
//         keyExtractor={(item, index) => index.toString()}
//         ItemSeparatorComponent = { this.FlatListItemSeparator }
//         renderItem={({ item }) => (
//           <TouchableOpacity onPress={ () => this.actionOnRow(item)}>
//                 <View>
//                 <View style={{ flexDirection: 'row'}}>
//                         <Text style={styles.cell_text}>{item.firstName}</Text>
//                     </View>
//                 </View>
//                 <View>
//                 <View style={{ flexDirection: 'row' }}>
//                         <Text style={styles.cell_text}>{item.email}</Text>
//                     </View>
//                 </View>
//           </TouchableOpacity>
//         )}
//         />
//             </View>
//         </View>
//     );
//     }
// }

// const styles = StyleSheet.create({   
//     cell_text: {
//       marginTop: 10,
//       marginBottom: 20,
//       marginLeft: 20,
//       fontSize:12
//     },
// });  

// export default Directory; 