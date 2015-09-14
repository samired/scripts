#By this script you can change file names in a directory
#change all the existing file names in a directory to new file names
# which are made up of sequencial numbers,
#
# eg.  <oldfilename_x.txt> to <001.txt>
#      <oldfilename_y.txt> to <002.txt>
#      <oldfilename_z.txt> to <003.txt>


import os

count = 1
for fn in os.listdir("."):
    if fn[-4:] == ".jpg":
        os.rename(fn, "%03i.jpg" % count)
        count = count + 1
