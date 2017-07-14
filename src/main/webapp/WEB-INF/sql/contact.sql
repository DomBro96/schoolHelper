#sql("getId")
  SELECT id, cid FROM contact WHERE id = #para(id) AND cid = #para(cid)
#end

#sql("getCid")
  SELECT id, cid FROM contact WHERE id = #para(id) AND cid = #para(cid)
#end

#sql("getList")
  SELECT id, cid FROM contact WHERE id = #para(id)
#end