module.exports = {
  env: {
    browser: true,
    es6: true,
    node: true
  },
  extends: ["eslint:recommended", "plugin:vue/vue3-strongly-recommended"],
  globals: {
    Atomics: "readonly",
    SharedArrayBuffer: "readonly"
  },
  // parser: "vue-eslint-parser",
  parserOptions: {
    ecmaVersion: 2018,
    sourceType: "module"
  },
  plugins: ["vue"],
  rules: {
    // vue html 태그 속성 한 줄에 몇개 넣을 수 있을 지 설정
    "vue/max-attributes-per-line": [
      "warn",
      {
        singleline: {
          max: 4,
          allowFirstLine: true
        },
        multiline: {
          max: 1,
          allowFirstLine: false
        }
      }
    ],
    indent: ["warn", 2],
    "vue/html-indent": ["warn", 2],
    "vue/singleline-html-element-content-newline": ["off"],
    "vue/html-quotes": ["off"],
    "vue/html-self-closing": ["off"]
  }
};
