module.exports = {
  env: {
    browser: true,
    es2021: true,
    node: true
  },
  plugins: [
    'jsdoc'
  ],
  extends: [
    'standard',
    'plugin:jsdoc/recommended'
  ],
  overrides: [
    {
      "files": ["*.mjs"],
      "parserOptions": {
        "sourceType": "module"
      }
    }
  ],
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module'
  },
  rules: {
  },
  ignorePatterns: [
    'build/',
    'doc/',
    'dist/',
    'node_modules/'
  ]
}
