#sql("get")
   SELECT id,username,password,gander,sign,facePath,account FROM users WHERE account = #para(account) and password = #para(password)
#end

#sql("account")
   SELECT id,username,password,gander,sign,facePath,account FROM users WHERE account = #para(account)
#end