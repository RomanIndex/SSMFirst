-------------------------查询剩余视频
select a.name, v.*
  from ucenter.qxy_videos v
  left join ucenter.qxy_author a on a.id = v.author_id
 where v.status = 1 and a.name like '%周小勇%'
   and not exists(select 1 from ucenter.qxy_active_video av where av.video_id = v.id)