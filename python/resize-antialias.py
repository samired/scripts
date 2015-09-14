from PIL import Image
import os , glob

size = 640 , 480

for image in glob.glob("*.jpg"):
    iml = Image.open(image)
    iml2 = iml.resize (size, Image.ANTIALIAS)
    iml2.save("s_" + image , "jpeg")
