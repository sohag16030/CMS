DELETE FROM address where 1=1;
DELETE FROM academic_info_subject where 1=1;
DELETE FROM academic_info where 1=1;
DELETE FROM cms_user where 1=1;

select * from address;
select * from academic_info_subject;
select * from academic_info;
select * from user_rating;
select * from cms_user;

select * from cms_user as u
join user_rating as r on r.user_rating_id = 2 and u.cms_user_id = 18;

select u.*, a.*, d.*, d2.*, u2.* from cms_user as u
join address a on u.cms_user_id = a.cms_user_id
join division d on a.division_id = d.division_id
join district d2 on a.district_id = d2.district_id
join upazila u2 on a.upazila_id = u2.upazila_id
and u.cms_user_id = 18;

select * from  cms_user as u
join academic_info as a on u.cms_user_id = a.cms_user_id
join academic_info_subject as ais on ais.academic_info_id = a.academic_info_id
join subject as s on ais.subject_id = s.subject_id
where u.cms_user_id = 18
