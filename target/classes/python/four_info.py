from faker import Faker

fake = Faker(locale='zh_cn')  # 指定fake 所属地


def four_info():
    """手机号码、姓名、邮箱、身份证号码"""
    mobile = fake.phone_number()
    name = fake.name()
    email = f'{mobile}@qq.com'
    id_card = fake.ssn()

    info_dict = {"name": name, "mobile": mobile, "id_card": id_card, "email": email}

    return info_dict

print(four_info())