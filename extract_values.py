from pymongo import MongoClient

# MongoDB connection settings
database_name = 'recipe-api-db'
uri = 'mongodb+srv://RecipeUser:9WG8f1FO6xoRCMYh@cluster1.zibb76c.mongodb.net'

# Connect to MongoDB
client = MongoClient(uri)
db = client[database_name]
collection = db['recipes']

# Extract unique categories and count items in each category
categories = {}
cursor = collection.find({})
for document in cursor:
    cuisine_path = document.get('cuisine_path')
    if cuisine_path:
        category = cuisine_path.split('/')[1]
        categories[category] = categories.get(category, 0) + 1

# Save results to a .txt file
output_file = 'type_counts.txt'
with open(output_file, 'w') as file:
    for category, count in categories.items():
        file.write(f'{category}: {count}\n')

print('Category counts saved to', output_file)

# Extract unique types and count items in each type
types = {}
cursor = collection.find({})
for document in cursor:
    recipe_type = document.get('Category')
    if recipe_type:
        types[recipe_type] = types.get(recipe_type, 0) + 1

# Save type counts to a .txt file
type_output_file = 'category_counts.txt'
with open(type_output_file, 'w') as file:
    for recipe_type, count in types.items():
        file.write(f'{recipe_type}: {count}\n')

print('Type counts saved to', type_output_file)

# Define time intervals
time_intervals = {
    '0-10 mins': (0, 10),
    '11-20 mins': (11, 20),
    '21-30 mins': (21, 30),
    '31-45 mins': (31, 45),
    '46-60 mins': (46, 60),
    '61-120 mins': (61, 120),
    'above 120 mins': (121, float('inf'))
}

# Initialize interval counts
interval_counts = {interval: 0 for interval in time_intervals}

# Helper function to convert time in hours and minutes to minutes
def convert_to_minutes(time):
    hours, mins = map(str.strip, time.split('hr'))
    if hours.isdigit():
        hours = int(hours)
    else:
        hours = 0

    mins_parts = mins.split()
    if mins_parts[0].isdigit():
        mins = int(mins_parts[0])
    else:
        mins = 0

    return hours * 60 + mins

# Iterate over documents and count intervals
cursor = collection.find({})
for document in cursor:
    total_time = document.get('total_time')
    if total_time:
        if 'hr' in total_time:
            total_time = convert_to_minutes(total_time)
        else:
            total_time = int(total_time.split()[0])
        
        for interval, (start, end) in time_intervals.items():
            if start <= total_time <= end:
                interval_counts[interval] += 1
                break

# Save interval counts to a .txt file
output_file = 'total_time_counts.txt'
with open(output_file, 'w') as file:
    for interval, count in interval_counts.items():
        file.write(f'{interval}: {count}\n')

print('Type counts saved to', type_output_file)

