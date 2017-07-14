#sql("getList")
   SELECT cid,mid,uid,comment,pubtime FROM comment WHERE mid = #(mid)
#end

