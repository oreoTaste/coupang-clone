-- 회원
CREATE TABLE Member (
	member_id  INTEGER      NOT NULL COMMENT '회원id', -- 회원id
	name       VARCHAR(50)  NULL     COMMENT '이름', -- 이름
	emoticon   VARCHAR(255) NULL     COMMENT '이모티콘', -- 이모티콘
	tel        VARCHAR(255) NULL     COMMENT '전화번호', -- 전화번호
	email      VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
	password   VARCHAR(255) NOT NULL COMMENT '비밀번호', -- 비밀번호
	type       VARCHAR(50)  NOT NULL COMMENT '회원유형', -- 회원유형
	address_id INTEGER      NULL     COMMENT '주소id' -- 주소id
)
COMMENT '회원';

-- 회원
ALTER TABLE Member
	ADD CONSTRAINT PK_Member -- 회원 기본키
		PRIMARY KEY (
			member_id -- 회원id
		);

-- 주문
CREATE TABLE Orders (
	order_id    INTEGER NOT NULL COMMENT '주문id', -- 주문id
	delivery_id INTEGER NOT NULL COMMENT '배송id', -- 배송id
	member_id   INTEGER NOT NULL COMMENT '회원id' -- 회원id
)
COMMENT '주문';

-- 주문
ALTER TABLE Orders
	ADD CONSTRAINT PK_Orders -- 주문 기본키
		PRIMARY KEY (
			order_id -- 주문id
		);

-- 상품
CREATE TABLE Product (
	product_id  INTEGER      NOT NULL COMMENT '상품id', -- 상품id
	category_id INTEGER      NOT NULL COMMENT '카테고리id', -- 카테고리id
	price       INTEGER      NOT NULL COMMENT '상품가(가격)', -- 상품가(가격)
	stock       INTEGER      NOT NULL COMMENT '재고(개수)', -- 재고(개수)
	name        VARCHAR(50)  NOT NULL COMMENT '상품명', -- 상품명
	model       VARCHAR(50)  NULL     COMMENT '모델명', -- 모델명
	description VARCHAR(255) NULL     COMMENT '상품설명', -- 상품설명
	comment_id  INTEGER      NULL     COMMENT '댓글id', -- 댓글id
	is_rocket   VARCHAR(255) NOT NULL COMMENT '로켓상품여부', -- 로켓상품여부
	thumbnail   VARCHAR(255) NULL     COMMENT '썸네일', -- 썸네일
	producer    VARCHAR(50)  NULL     COMMENT '제조사', -- 제조사
	origin      VARCHAR(50)  NULL     COMMENT '제조국' -- 제조국
)
COMMENT '상품';

-- 상품
ALTER TABLE Product
	ADD CONSTRAINT PK_Product -- 상품 기본키
		PRIMARY KEY (
			product_id -- 상품id
		);

-- 주문상품
CREATE TABLE OrdersProduct (
	order_product_id INTEGER NOT NULL COMMENT '주문상품id', -- 주문상품id
	order_id         INTEGER NOT NULL COMMENT '주문id', -- 주문id
	price            INTEGER NOT NULL COMMENT '상품가격', -- 상품가격
	product_id       INTEGER NOT NULL COMMENT '상품id', -- 상품id
	stock            INTEGER NOT NULL COMMENT '판매개수' -- 판매개수
)
COMMENT '주문상품';

-- 주문상품
ALTER TABLE OrdersProduct
	ADD CONSTRAINT PK_OrdersProduct -- 주문상품 기본키
		PRIMARY KEY (
			order_product_id -- 주문상품id
		);

-- 배송
CREATE TABLE Delivery (
	delivery_id INTEGER NOT NULL COMMENT '배송id', -- 배송id
	address_id  INTEGER NOT NULL COMMENT '주소id' -- 주소id
)
COMMENT '배송';

-- 배송
ALTER TABLE Delivery
	ADD CONSTRAINT PK_Delivery -- 배송 기본키
		PRIMARY KEY (
			delivery_id -- 배송id
		);

-- 카테고리
CREATE TABLE Category (
	category_id INTEGER     NOT NULL COMMENT '카테고리id', -- 카테고리id
	big         VARCHAR(50) NOT NULL COMMENT '대분류', -- 대분류
	medium      VARCHAR(50) NOT NULL COMMENT '중분류', -- 중분류
	small       VARCHAR(50) NOT NULL COMMENT '소분류' -- 소분류
)
COMMENT '카테고리';

-- 카테고리
ALTER TABLE Category
	ADD CONSTRAINT PK_Category -- 카테고리 기본키
		PRIMARY KEY (
			category_id -- 카테고리id
		);

-- 댓글
CREATE TABLE Comment (
	comment_id INTEGER      NOT NULL COMMENT '댓글id', -- 댓글id
	member_id  INTEGER      NOT NULL COMMENT '회원id', -- 회원id
	review    VARCHAR(255) NULL     COMMENT '후기' -- 후기
)
COMMENT '댓글';

-- 댓글
ALTER TABLE Comment
	ADD CONSTRAINT PK_Comment -- 댓글 기본키
		PRIMARY KEY (
			comment_id -- 댓글id
		);

-- 주소
CREATE TABLE Address (
	address_id INTEGER      NOT NULL COMMENT '주소id', -- 주소id
	city       VARCHAR(255) NOT NULL COMMENT '도시명', -- 도시명
	street     VARCHAR(255) NOT NULL COMMENT '도로명', -- 도로명
	zipcode    VARCHAR(255) NOT NULL COMMENT '우편번호' -- 우편번호
)
COMMENT '주소';

-- 주소
ALTER TABLE Address
	ADD CONSTRAINT PK_Address -- 주소 기본키
		PRIMARY KEY (
			address_id -- 주소id
		);

-- 상품평가
CREATE TABLE Evaluation (
	evaluation_id INTEGER NOT NULL COMMENT '상품평가id', -- 상품평가id
	member_id     INTEGER NOT NULL COMMENT '회원id', -- 회원id
	product_id    INTEGER NOT NULL COMMENT '상품id', -- 상품id
	grade         INTEGER NOT NULL COMMENT '평점' -- 평점
)
COMMENT '상품평가';

-- 상품평가
ALTER TABLE Evaluation
	ADD CONSTRAINT PK_Evaluation -- 상품평가 기본키
		PRIMARY KEY (
			evaluation_id -- 상품평가id
		);

-- 회원
ALTER TABLE Member
	ADD CONSTRAINT FK_Address_TO_Member -- 주소 -> 회원
		FOREIGN KEY (
			address_id -- 주소id
		)
		REFERENCES Address ( -- 주소
			address_id -- 주소id
		);

-- 주문
ALTER TABLE Orders
	ADD CONSTRAINT FK_Member_TO_Orders -- 회원 -> 주문
		FOREIGN KEY (
			member_id -- 회원id
		)
		REFERENCES Member ( -- 회원
			member_id -- 회원id
		);

-- 주문
ALTER TABLE Orders
	ADD CONSTRAINT FK_Delivery_TO_Orders -- 배송 -> 주문
		FOREIGN KEY (
			delivery_id -- 배송id
		)
		REFERENCES Delivery ( -- 배송
			delivery_id -- 배송id
		);

-- 상품
ALTER TABLE Product
	ADD CONSTRAINT FK_Category_TO_Product -- 카테고리 -> 상품
		FOREIGN KEY (
			category_id -- 카테고리id
		)
		REFERENCES Category ( -- 카테고리
			category_id -- 카테고리id
		);

-- 상품
ALTER TABLE Product
	ADD CONSTRAINT FK_Comment_TO_Product -- 댓글 -> 상품
		FOREIGN KEY (
			comment_id -- 댓글id
		)
		REFERENCES Comment ( -- 댓글
			comment_id -- 댓글id
		);

-- 주문상품
ALTER TABLE OrdersProduct
	ADD CONSTRAINT FK_Orders_TO_OrdersProduct -- 주문 -> 주문상품
		FOREIGN KEY (
			order_id -- 주문id
		)
		REFERENCES Orders ( -- 주문
			order_id -- 주문id
		);

-- 주문상품
ALTER TABLE OrdersProduct
	ADD CONSTRAINT FK_Product_TO_OrdersProduct -- 상품 -> 주문상품
		FOREIGN KEY (
			product_id -- 상품id
		)
		REFERENCES Product ( -- 상품
			product_id -- 상품id
		);

-- 배송
ALTER TABLE Delivery
	ADD CONSTRAINT FK_Address_TO_Delivery -- 주소 -> 배송
		FOREIGN KEY (
			address_id -- 주소id
		)
		REFERENCES Address ( -- 주소
			address_id -- 주소id
		);

-- 댓글
ALTER TABLE Comment
	ADD CONSTRAINT FK_Member_TO_Comment -- 회원 -> 댓글
		FOREIGN KEY (
			member_id -- 회원id
		)
		REFERENCES Member ( -- 회원
			member_id -- 회원id
		);

-- 상품평가
ALTER TABLE Evaluation
	ADD CONSTRAINT FK_Member_TO_Evaluation -- 회원 -> 상품평가
		FOREIGN KEY (
			member_id -- 회원id
		)
		REFERENCES Member ( -- 회원
			member_id -- 회원id
		);

-- 상품평가
ALTER TABLE Evaluation
	ADD CONSTRAINT FK_Product_TO_Evaluation -- 상품 -> 상품평가
		FOREIGN KEY (
			product_id -- 상품id
		)
		REFERENCES Product ( -- 상품
			product_id -- 상품id
		);