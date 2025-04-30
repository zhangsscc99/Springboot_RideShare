module.exports = {
  root: true,
  env: {
    node: true,
    es6: true,
    browser: true
  },
  extends: [
    'eslint:recommended'
  ],
  parserOptions: {
    ecmaVersion: 2020,
    sourceType: 'module'
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-undef': 'off'
  },
  // Vue文件解析设置
  overrides: [
    {
      files: ['*.vue'],
      // Vue文件不执行严格检查
      parser: 'vue-eslint-parser',
      parserOptions: {
        parser: 'babel-eslint'
      },
      rules: {
        'no-unused-vars': 'off'
      }
    }
  ]
} 