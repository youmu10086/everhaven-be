# 前端对接指南 - RSA 加密

后端接口 `/user/public-key` 返回的是 Base64 编码的公钥字符串（X.509 格式）。前端在发送敏感数据（如登录/注册时的密码）前，需要使用该公钥进行加密。

## 1. 安装依赖

推荐使用 `jsencrypt` 库。

```bash
npm install jsencrypt
# 或
yarn add jsencrypt
```

## 2. 加密工具类示例 (TypeScript/JavaScript)

由于后端返回的公钥不包含 PEM 格式的头尾（`-----BEGIN PUBLIC KEY-----`），前端通常需要手动拼接。

```typescript
import JSEncrypt from 'jsencrypt';

/**
 * RSA 加密方法
 * @param plainText 明文（如原始密码）
 * @param publicKeyBase64 后端返回的公钥字符串
 * @returns 加密后的 Base64 字符串，如果失败返回 false/null
 */
export function encryptPassword(plainText, publicKeyBase64) {
  const encryptor = new JSEncrypt();
  
  // 拼接 PEM 格式头尾，确保 jsencrypt 能正确识别
  // 注意：后端返回的是 X.509 格式公钥，对应的头尾是 PUBLIC KEY
  const formattedPublicKey = `-----BEGIN PUBLIC KEY-----
${publicKeyBase64}
-----END PUBLIC KEY-----`;

  encryptor.setPublicKey(formattedPublicKey);
  
  // encrypt 方法返回的是 Base64 编码的密文
  return encryptor.encrypt(plainText);
}
```

## 3. 调用流程示例

1. **进入页面时**（如登录页），调用 `GET /user/public-key` 获取公钥并暂存。
2. **用户提交时**，使用暂存的公钥加密密码。
3. **发送请求**，将加密后的字符串作为 `password` 字段发送给后端。

```javascript
import axios from 'axios';
import { encryptPassword } from './utils/encrypt'; // 假设上面的工具在一个单独的文件中

// 1. 获取公钥
let publicKey = '';
async function fetchPublicKey() {
  const res = await axios.get('/user/public-key');
  if (res.data.code === 200) {
    publicKey = res.data.data;
  }
}

// 2. 登录提交
async function handleLogin(username, password) {
  if (!publicKey) {
    await fetchPublicKey();
  }

  // 加密密码
  const encryptedPass = encryptPassword(password, publicKey);
  
  if (!encryptedPass) {
    console.error('加密失败');
    return;
  }

  // 发送请求
  const loginData = {
    username: username,
    password: encryptedPass // 发送密文
  };

  const res = await axios.post('/user/login', loginData);
  // ... 处理登录成功逻辑
}
```

