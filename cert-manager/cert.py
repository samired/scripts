import os, sys

from PIL import Image
from PIL import ImageFont
from PIL import ImageDraw 


# font = ImageFont.truetype(<font-file>, <font-size>)
font1 = ImageFont.truetype("filt.ttf", 25)
font2 = ImageFont.truetype("filt.ttf", 26)
font3 = ImageFont.truetype("micross.ttf", 15)

course_name = "Introduction to Hydrocarbon Interpretation"
course_date = "11 August 2015 - Cairo"

last_serial = 26

attendees = ['Ahmed Gamal', 'Ahmed Nasser', 'Hatem Asran', 'Hossam Ibrahim', 'Mohamed Mokhtar']

for attendee in attendees:
	img = Image.open("cert.jpg")
	draw = ImageDraw.Draw(img)

	draw.text((490, 859./2),attendee,(70,70,70),font=font1)
	draw.text((325, 540),course_name,(70,70,70),font=font2)
	draw.text((420, 600),course_date,(70,70,70),font=font1)
	
	draw.text((113, 735),"SN:2015-IST-HEI-"+str(last_serial),(70,70,70),font=font3)
	last_serial = last_serial+1
		
	img.save(attendee+'.png', 'PNG')
	
