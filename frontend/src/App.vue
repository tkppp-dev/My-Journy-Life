<template>
  <router-view />
</template>

<script>
export default {};
</script>

<style lang="sass">
@import './sass/main.scss'
</style>

<style>
@font-face {
  font-family: 'apple_sd_gothic_neo';
  src: url('./assets/fonts/AppleSDGothicNeoL.woff') format('woff')
    url('./assets/fonts/AppleSDGothicNeoL.woff2') format('woff2');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'nanum_gothic';
  src: url('./assets/fonts/nanum-gothic-regular.ttf') format('ttf');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'multicolore_regular';
  src: url('./assets/fonts/multicolore-webfont.woff2') format('woff2'),
    url('./assets/fonts/multicolore-webfont.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

button {
  border: none;
  background-color: transparent;
  cursor: pointer;
}
</style>
