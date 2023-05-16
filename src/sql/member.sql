-- member 계정 생성  @system
alter session set "_oracle_script" = true;

create user member
identified by member
default tablespace users;

grant connect, resource to member;

alter user member quota unlimited on users;


-- drop table member;
create table member (
    id varchar2(20) not null,
    password varchar2(100) not null,
    name varchar2(100) not null,
    phone varchar2(11) not null,
    point number default 0 ,
    reg_date date default sysdate,
    del_date date,
    state_yn char(1) default 'Y',
    constraints pk_member_id primary key (id),
    constraints ck_member_point check(point>=0),
    constraints ck_member_state_yn check(state_yn in ('Y', 'N'))
);
insert into member values('lhl', 'lhl' , '이혜령', '01012341234', 40000, default,null,default);
insert into member values('jsk', 'jsk' , '전수경', '01012331244', 8000, default,null,default);
insert into member values('shj', 'shj' , '신희진', '01033214321', 3000, default,null,default);



-- drop table member_log;
create table member_log (
    id varchar2(20) not null,
    day date default sysdate, 
    servicename varchar2(100) default 'standard' not null,
    no varchar2(10) not null,
    point number default 0,
    add_point number,
       
    constraint  fk_member_log_id foreign key(id) references member(id),
    constraints ck_member_log_point check(point>=0),
    constraints ck_member_log_add_point check(add_point>=0),
    constraints ck_member_log_use_point check(use_point>=0)
);

-- drop table manager_log;
create table manager_log (
    log_no varchar2(10) not null,
    id varchar2(20) not null,
    use_date date not null,
    use_mode varchar2(100) default 'standard' not null,
    charge_date date not null,
    income number,
    constraints uq_manager_log_log_no unique(log_no)
);

-- drop table machine;
create table machine (
    no varchar2(100) not null,
    state varchar2(100) default 'available' not null,
    service_name varchar2(100) default 'standard' ,
    s_time timestamp default sysdate ,
    q_time timestamp,
    constraints pk_laundary_state_no primary key(no)
);

insert into machine values('1' , 'use' null, default,sysdate +(1/24));
insert into machine values('2' , default , ' ', default, null);
insert into machine values('3' , 'use' , default, default, sysdate +(1/24));
insert into machine values('4' , default, ' ', default, null);
insert into machine values('5' , 'use' ,default, default, sysdate +(1/24));

--확인용
select*from member;
select *from member_log;
select *from machine;
select * from machine where state = 'available';

update machine set state = 'available' where no in('1','3','5');
commit;

--create or replace trigger trig_insert_member_log
--    after insert on member_log
--    for each row
--begin
--    update machine 
--    set service_name = new.service_name
--     ,state = 'use'
--     ,s_time= sysdate
--     ,q_time= sysdate + decode(new.service_name, 'brain', 10/(24*60*60), 1/24)
--    where no = :new.no;
--
--end;
--/






select * 
from member 
where id = '1';



and password = :2 


, 
OriginalSql = select * from member where id = ? and password = ?;





select * from member where id = '1' and password = '1';




select * From member;
