import cv2



img_path = 'COCO_train2014_000000001375.jpg'

# 단순화
img = cv2.imread(img_path)
# 부드럽게
blr = cv2.bilateralFilter(img, 10, 50, 50)
# 흑백
cvt = cv2.cvtColor(blr, cv2.COLOR_BGR2GRAY)
# 이진화
ret, bina = cv2.threshold(cvt, 127, 255 ,cv2.THRESH_BINARY_INV)
# 흑백 거꾸로
binary = cv2.bitwise_not(bina)
# 윤곽선
contours, hierarchy = cv2.findContours(binary, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_NONE)


for i in range(len(contours)):
    cv2.drawContours(img, [contours[i]], 0, (50, 50, 50), 2)
    # cv2.putText(img, str(i), tuple(contours[i][0][0]), cv2.FONT_HERSHEY_COMPLEX, 0.8, (0, 255, 0), 1)
    print(i, hierarchy[0][i])
    # cv2.imshow("src", img)
    # cv2.waitKey(0)

# 카툰화
edge = 255 - cv2.Canny(blr, 80, 110)
edge = cv2.cvtColor(edge, cv2.COLOR_GRAY2BGR)
dst = cv2.bitwise_and(blr, edge)

print(type(img))
print(img.shape)
print(type(dst))
print(dst.shape)

cv2.imwrite(f'images/{img_path}', img)
cv2.imshow('img', img)
cv2.imshow('dst', dst)
cv2.imshow('thres', binary)
cv2.waitKey(0)
cv2.destroyAllWindows()

