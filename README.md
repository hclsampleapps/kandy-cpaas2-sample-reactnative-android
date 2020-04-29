# kandy-cpaas2-sample-reactnative-android

![Maintenance](https://img.shields.io/maintenance/no/2019?style=flat-square)

ReactNative based audio-video app is used to create communication channel between two users via Voice/Video APIs

## Introduction 
This app is used to establish a communication channel between 2 or more users via available Kandy APIs for *SMS*, *Chat* & *Voice/Video* along with other UC features like *Presence* & *Directory*.

### Available Features
- [x] SMS
- [x] Chat
- [ ] Voice/Video
- [x] Address-book & Directory
- [x] Presence

## Getting Started

### User manual 

1. Create an account on **AT&T** portal via [Register now for a free account](https://apimarket.att.com/signup).
2. Open 2 instances of `index.html` in the browser for *User1* and *User2*.
3. Enter the *server URL*, for e.g.,
	- For AT&T API Marketplace [apimarket.att.com](https://apimarket.att.com), enter `https://oauth-cpaas.att.com`
4. Choose to get accessToken by *Password Grant* flow or *Client Credentials* flow.
5. Login using two different users' credentials in both the browser windows.
6. For **Password Grant** flow, enter 
	- *clientId* 
	- *emailId* 
	- *password*  
7. For **Client Credentials Grant** flow, enter
	- *privateKey*
	- *privateSecret*   
8. Click ***Login***
7. After successful login you can proceed further accordingly.

##### Notes

 - Existing user can confirm their account via [Log in to AT&T API Marketplace](https://apimarket.att.com/login)
 - You can download *kandy.js* from [Developer documentation - SDKs](https://apimarket.att.com/developer/sdks/javascript)
 - For more information about React-native [React-native documentation](https://facebook.github.io/react-native/docs/getting-started)


## Build and Test
Setup the repository to build the code and run the app. 

### Setup

1. Install **node.js**
2. Install **react-native**, via
```shell
$ npm install -g react-native-cli
```
3. Clone the project, via
```shell
$ git clone https://github.com/ribbon-abku/kandy-cpaas2-sample-reactnative-android.git
```
   Alternatively, you can directly download it as well.

### Build & Run

1. Get inside the cloned/downloaded repository as 
```shell
$ cd CPaaS_ReactNative_Android   
```
2. Install all dependencies, via
```shell
$ npm install
```
3. Run and build the app, via
```shell
$ react-native run-android
```
4. Start server, via
```shell
$ react-native start
```

###  Troubleshooting tips

1.  Install Android SDK.
2.  Add path in environment.
3.  Install JDK latest version.
4.  Connect one android device or emulator.

## Contribute

#### Branching strategy

To learn about the branching strategy, contribution & coding conventions followed in the project, please refer [GitFlow based branching strategy for your project repository](https://gist.github.com/ribbon-abku/10d3fc1cff5c35a2df401196678e258a)
