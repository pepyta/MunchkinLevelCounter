import * as React from 'react';
import { Image, View, StyleSheet, ScrollView } from 'react-native';
import { Banner, FAB, Appbar, Snackbar, Provider, DefaultTheme, Text, Headline, Title, IconButton, Divider } from 'react-native-paper';

const theme = {
  ...DefaultTheme,
  roundness: 2,
  colors: {
    ...DefaultTheme.colors,
    primary: '#49250b',
    accent: '#2c1200',
    background: '#ffd09a'
  },
};

export default class App extends React.Component {

  state = {
    nfcVisible: false,
    snackbarVisible: false, // disable till we don't have this feature
    rollButtonVisible: true,
    roll: parseInt(Math.random() * 6 + 1),
    characterLevel: 1,
    itemBonus: 0
  };

  render() {
    return (
      <Provider theme={theme}>

        <View style={styles.view}>
          <Appbar.Header>
            <Appbar.Content
              title="Munchkin"
            />
          </Appbar.Header>
          <Banner
            visible={this.state.nfcVisible}
            actions={[
              {
                label: 'Enable',
                onPress: () => this.setState({ nfcVisible: false }),
              },
              {
                label: 'Dismiss',
                onPress: () => this.setState({ nfcVisible: false }),
              },
            ]}
            icon="nfc"
          >
            In order to enable instant fighting enable NFC on your device!
        </Banner>
          <ScrollView style={styles.app}>
            <View style={styles.row}>
              <View style={styles.centerGrid}>
                <IconButton icon="minus" onPress={() => this.setState({
                  characterLevel: (this.state.characterLevel > 1 ? this.state.characterLevel-1 : 1)
                })} />
              </View>

              <View style={styles.centerGrid}>
                <Title style={{
                  textAlign: 'center'
                }}>Character level</Title>
                <Headline>{this.state.characterLevel}</Headline>
              </View>
              <View style={styles.centerGrid}>
                <IconButton icon="plus" onPress={() => this.setState({
                  characterLevel: (this.state.characterLevel < 21 ? this.state.characterLevel+1 : 21)
                })} />
              </View>
            </View>
            <View style={styles.row}>
              <View style={styles.centerGrid}>
                <IconButton icon="minus" onPress={() => this.setState({
                  itemBonus: this.state.itemBonus-1
                })} />
              </View>

              <View style={styles.centerGrid}>
                <Title style={{
                  textAlign: 'center'
                }}>Item levels</Title>
                <Headline>{this.state.itemBonus}</Headline>
              </View>
              <View style={styles.centerGrid}>
                <IconButton icon="plus" onPress={() => this.setState({
                  itemBonus: this.state.itemBonus+1
                })} />
              </View>
            </View>
            <Divider />
            <View style={styles.row}>
              <View style={styles.centerGrid}>
                <Title style={{
                  textAlign: 'center'
                }}>Overall points</Title>
                <Headline>{this.state.itemBonus + this.state.characterLevel}</Headline>
              </View>
            </View>
          </ScrollView>
          <FAB
            style={styles.fab}
            visible={this.state.rollButtonVisible}
            icon={({ size }) =>
              <Image
                source={require('./assets/dice.png')}
                style={{
                  width: size,
                  height: size,
                }}
              />
            }
            onPress={() =>
              this.setState({
                roll: parseInt(Math.random() * 6 + 1),
                snackbarVisible: true,
                rollButtonVisible: false
              })
            }
          />

          <Snackbar
            visible={this.state.snackbarVisible}
            onDismiss={() => {
              this.setState({
                snackbarVisible: false,
                rollButtonVisible: true
              })
            }}
            style={styles.lightSnackbar}
            action={{
              label: 'Okay',
              onPress: () => {
                this.setState({
                  snackbarVisible: false,
                  rollButtonVisible: true
                })
              },
            }}
          >
            <Text style={{
              color: '#49250b'
            }}>
              You've rolled a {this.state.roll}!
            </Text>
          </Snackbar>
        </View>
      </Provider>
    );
  }
}

const styles = StyleSheet.create({
  fab: {
    position: 'absolute',
    margin: 16,
    right: 0,
    bottom: 0
  },
  view: {
    flex: 1
  },
  app: {
    margin: 16
  },
  centerAlign: {
    justifyContent: 'center',
    alignItems: 'center',
    textAlign: 'center'
  },
  lightSnackbar: {
    backgroundColor: '#fff',
    borderRadius: 7
  },
  centerGrid: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    textAlign: 'center',
    alignContent: 'center',
  },
  row: {
    flexDirection: 'row',
    marginTop: 24,
    marginBottom: 24
  }
})
