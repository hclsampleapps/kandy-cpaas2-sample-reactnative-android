### Troubleshooting

1. Getting 'unable to load script' error.
   - Make sure you are running a metro server via `run command react-native start`

2. Could not connect to development server on android.
   - `Connect device`
   - `run command adb reverse tcp:8081 tcp:8081`

3. On executing `react-native run-android` command, the SDK location not found but it exists in the path.
   - `Add path in environment`
