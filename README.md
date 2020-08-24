# kandy-cpaas2-sample-reactnative-android
ReactNative based sample app of CPaaS2 modules (SMS, Chat, Presence, Address book)

### Install as Android App
You can download the .apk file from [releases](https://github.com/hclsampleapps/kandy-cpaas2-sample-reactnative-androi/releases) section.
If you are a developer, then you can generate the apk from source code.

### Available Features
- [x] SMS
- [x] Chat
- [ ] Voice/Video
- [x] Address-book & Directory
- [ ] Presence

### Run this Android App
1. Open Android App
2. Enter the credentials of a user.

### Execute commands for development
1. Setup repository via `git clone https://github.com/hclsampleapps/kandy-cpaas2-sample-reactnative-android`
2. Resolve build dependency via sync gradle in android studio
3. Generate final build by android studio

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
$ cd kandy-cpaas2-sample-reactnative-android   
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

### User manual 

1. Create an account on **AT&T** portal via [Register now for a free account](https://apimarket.att.com/signup).
2. Open application in 2 android devices with *User1* and *User2*.
3. Enter the *server URL*, for e.g.,
	- For AT&T API Marketplace [apimarket.att.com](https://apimarket.att.com), enter `https://oauth-cpaas.att.com`
4. Choose to get accessToken by *Password Grant* flow.
5. Login using two different users' credentials in application.
6. For **Password Grant** flow, enter 
	- *clientId* 
	- *emailId* 
	- *password*   
7. Click ***Login***
8. After successful login you can proceed further accordingly.


## Contribute

#### Branching strategy

To learn about the branching strategy, contribution & coding conventions followed in the project, please refer [GitFlow based branching strategy for your project repository](https://gist.github.com/ribbon-abku/10d3fc1cff5c35a2df401196678e258a)