const express = require('express');
const path = require('path');

const app = express();
const host = '172.30.1.3'; // 변경된 호스트
const port = 8080; // 변경된 포트

// 정적 파일 제공
app.use(express.static(path.join(__dirname, 'dist')));

// 모든 경로에 대해 index.html 반환
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, 'dist', 'index.html'));
});

app.listen(port, host, () => {
    console.log(`Server is running on http://${host}:${port}`);
});
