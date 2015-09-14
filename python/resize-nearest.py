import Image
import os , glob

size = 800 , 600

for image in glob.glob("*.jpg"):
    image2 = Image.open(image)
    iml = image2.resize(size, Image.NEAREST)
    iml.save("s_" + image , "jpeg")
