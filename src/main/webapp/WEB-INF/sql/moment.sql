#sql("getAll")
   SELECT mid,uid,moment,pubtime FROM moment
#end

#sql("deleteMoment")
   SELECT mid,uid,moment,pubtime FROM moment WHERE mid = #(mid)
#end

#sql("get")
   SELECT mid,uid,moment,pubtime FROM moment WHERE uid = #(uid)
#end

