//run.js
const { exec } = require('child_process')
exec('java -jar  /easy_blog.jar --spring.profiles.active=pro ',(error, stdout, stderr) => {
  if(error){
    console.log(`exec error: ${error}`)
    return
  }
  console.log(`stdout: ${stdout}`);
  console.log(`stderr: ${stderr}`);
})
