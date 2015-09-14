import os

order = [0,1,2,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,3,4,5,6]

for f in os.listdir('.'):
  list(f)
  d = [f[i] for i in order]
  os.rename(f,"".join(d)) 

# I used this script to rename attendance files with countries
# at the end of the file name
