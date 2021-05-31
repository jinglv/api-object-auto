import random
import ssl
from bank_card import BankCard

# ssl取消全局验证
ssl._create_default_https_context = ssl._create_unverified_context


def bank_random():
    """生成银行卡 校验版"""
    while True:
        bank = '62148301' + '%s' % (random.randint(0, int(8 * '9')))
        if len(bank) == 16:
            banks = BankCard(bank)
            if banks.to_dict().get("validated") is True:
                break
            else:
                continue

    return bank


print(bank_random())