module.exports = {
  preset: '@vue/cli-plugin-unit-jest',
  transform: {
    '^.+\\.vue$': 'vue-jest',
  },
  moduleNameMapper: {
    '/^@/(.*)$/': '/Users/tkppp/WorkSpace/my_journy_life/frontend/src/$1',
  },
  resolver: null,
};
