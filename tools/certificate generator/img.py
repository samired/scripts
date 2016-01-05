import os

from PIL import Image
from PIL import ImageFont
from PIL import ImageDraw 


# font = ImageFont.truetype(<font-file>, <font-size>)
font1 = ImageFont.truetype("filt.ttf", 25)
font2 = ImageFont.truetype("filt.ttf", 26)
font3 = ImageFont.truetype("micross.ttf", 15)

course_name = "Oilfield Familiarization - Summer Training"
course_date = "17 - 21 August 2014 - Cairo"

last_serial = 26

attendees = ['Ahmed Ali Dahish', 'Ahmed Fawzy Sharabi']

for attendee in attendees:
	img = Image.open("test.jpg")
	draw = ImageDraw.Draw(img)

	draw.text((440, 859./2),attendee,(70,70,70),font=font1)
	draw.text((325, 540),course_name,(70,70,70),font=font2)
	draw.text((420, 600),course_date,(70,70,70),font=font1)
	
	draw.text((113, 735),"SN:2014-IST-OFF-"+str(last_serial),(70,70,70),font=font3)
	last_serial = last_serial+1
		
	img.save(attendee+'.png', 'PNG')
	
